import re
import sys

##### VALIDATE USER INPUT

# All user input should:
# 1. Start with a capital letter
# 2. Be followed by one or more lower case letters
# 3. Optionally be followed by a space. If a space is there, it should be followed by one or more upper/lower case characters
# 4. Then the string should end
invalid_user_input = []

USER_INPUT_REGEX = r'^[A-Za-z]+( [A-Za-z]+)*$'

def validate_user_input(variable_value):
    if not re.match(USER_INPUT_REGEX, variable_value):
        invalid_user_input.append(variable_value)
        
validate_user_input('{{cookiecutter.project_name}}')
validate_user_input('{{cookiecutter.group_name}}')

if invalid_user_input:
    print(f'The following values are invalid: {invalid_user_input}')
    print('Valid values match the following regex: "^[A-Za-z]+( [A-Za-z]+)*$"')
    sys.exit(1)

##### SET-UP VARIABLES FOR USE IN PROJECT GENERATION

'{{ cookiecutter.update({ "_project_name_kebab_case": cookiecutter.project_name|lower|replace(" ", "-") }) }}'
'{{ cookiecutter.update({ "_project_name_lowercase_no_spaces": cookiecutter.project_name|lower|replace(" ", "") }) }}'
'{{ cookiecutter.update({ "_project_name_camel_case": cookiecutter.project_name|title|replace(" ", "") }) }}'
'{{ cookiecutter.update({ "_group_name_lowercase_no_spaces": cookiecutter.project_name|lower|replace(" ", "") }) }}'