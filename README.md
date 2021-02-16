# F21CA: RoboCafe (Group 3)
This repo contains the Rasa bot used in the RoboCafe project for the F21CA module.
=======
## Installation
In order to run the Rasa bot we encourage you to use the Alana [Anaconda](https://www.anaconda.com/) environment found in this repo. 

You can do this by running the alana_installation.sh in the Anaconda prompt (on Windows) or terminal (MacOS / Unix). 

Once created activate it with conda activate Alana, you will see (Alana) at the left of your prompt.



Install Rasa with `pip install --no-cache-dir rasa`

Then clone this repo to the local machine, this can be done with the GitHub desktop app or with the `git clone https://github.com/carlobee/RoboCafe` command in the terminal (MacOS / Unix). 

After this is done navigate to the directory in the terminal / Anaconda prompt and run `rasa train`

You can then run an interactive shell by running `rasa shell`

## Development
**When pushing changes don't push to main branch. Create your own branch or push to the branch for your team (e.g. NLU).**