# F21CA: RoboCafe (Group 3)
=======
This repo contains the Rasa bot used in the RoboCafe project for the F21CA module.

## Rasa and Rasa X Installation
In order to run the Rasa bot we encourage you to use the Alana [Anaconda](https://www.anaconda.com/) environment found in this repo.   
You can do this by running the alana_installation.sh in the Anaconda prompt (on Windows) or terminal (MacOS / Unix) or by using the terminal in pycharm. Once created activate it with conda activate Alana, you will see (Alana) at the left of your prompt.

### Rasa
For testing we recommend installing Rasa

```bash
pip install rasa
```

### Rasa X
For development we recommend installing Rasa X.  
This gives more features for training the bot.

```bash
pip install rasa-x --extra-index-url https://pypi.rasa.com/simple
```

*If this installation is very slow try downgrading your pip installation to 20.2 with: `pip install --upgrade pip==20.2`*

You may have to install [Visual C++ build tools](https://go.microsoft.com/fwlink/?LinkId=691126) which consist of 3GB+ of files.  
This is not required in normal Rasa but is for Rasa X

### Cloning repo
This can be done with the GitHub desktop app or with the following command in the terminal (MacOS / Unix).  
```bash
git clone https://github.com/carlobee/RoboCafe
```

### Running Rasa
Before running rasa make sure to run the actions server in a separate prompt or terminal window/tab.
```bash
rasa run actions
```
You can then run either of the following:  
`rasa shell` runs a command line chat interface.  
`rasa interative` runs a command line debug interface.

### Running Rasa X
Same as Rasa but you can run an interactive web interface by running:
```bash
rasa x
```
This allows you to train your bot in real time and save different models.

## Telegram server setup
This requires [ngrok](https://ngrok.com/), which creates a unique URL that can be used to access your bot server outside your local network.   
Once you have downloaded ngrok navigate to the folder in your prompt/terminal and run the command `ngrok http 5005`

The window should display a forwarding URL (e.g.  https://6e28a3215a9d.ngrok.io -> http://localhost:5005),  
copy the first address (https://6e28a3215a9d.ngrok.io) to the `webhook_url` field in the credientials.yml file.
```python
webhook_url: "https://<your-ngrok-url>/webhooks/telegram/webhook"
```

Open a new terminal/prompt window or tab and use the command `rasa run` to run the bot

Now open your [Telegram](https://web.telegram.org/) app and add the user @robot_cafe_bot to start chatting!

## Development

**When pushing changes don't push to main branch. Create your own branch or push to the branch for your team (e.g. 
RASA).**

**Pull requests will be reviewed by the whole team before merging with the master branch**
