#!/bin/bash
#
# A script to update your /etc/hosts file from minikube ingest records and start a minikube tunnel

set -e

MINIKUBE_IP=$(minikube ip)
APP_LOCAL_URL="{{cookiecutter._project_name_kebab_case}}.local"
HOSTS_ENTRY="$MINIKUBE_IP $APP_LOCAL_URL"
echo -e "\n- Your Minikube IP is: $MINIKUBE_IP"
echo -e "- The hosts entry to be added/updated is: $HOSTS_ENTRY\n"

if grep -Fq "$APP_LOCAL_URL" /etc/hosts > /dev/null
then
    echo -e "- You already have an entry for $APP_LOCAL_URL
 in your /etc/hosts file. Program exiting..."
elif grep -Fq "$MINIKUBE_IP" /etc/hosts > /dev/null
then
    echo -e "- Adding $APP_LOCAL_URL
 to the existing $MINIKUBE_IP entry in /etc/hosts\n"
    PrintEtcHosts "BEFORE"
    sudo sed -i "/^$MINIKUBE_IP/ s/$/ $APP_LOCAL_URL
/" /etc/hosts
    PrintEtcHosts "AFTER"
    StartMinikubeTunnel
else
    echo -e "- Adding $HOSTS_ENTRY to the end of /etc/hosts\n"
    PrintEtcHosts "BEFORE"
    echo "$HOSTS_ENTRY" | sudo tee -a /etc/hosts
    PrintEtcHosts "AFTER"
    StartMinikubeTunnel
fi

PrintEtcHosts () {
    echo -e "/etc/hosts $1\n"
    echo -e "'''\n"
    sudo cat /etc/hosts
    echo -e "'''\n"
}

StartMinikubeTunnel () {
    echo "- Setting up Minikube tunnel so you can access $APP_LOCAL_URL
 from your browser/terminal. Expect a sudo password request next..."
    minikube tunnel
}