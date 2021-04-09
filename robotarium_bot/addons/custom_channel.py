import asyncio
import inspect
import json
from sanic import Sanic, Blueprint, response
from sanic.request import Request
from sanic.response import HTTPResponse
from typing import Text, Dict, Any, Optional, Callable, Awaitable, NoReturn
from utils import abstract_classes
import logging
import rasa.utils.endpoints
from rasa.core.channels.channel import (
    InputChannel,
    CollectingOutputChannel,
    UserMessage,
)

class MyIO(InputChannel):
    logger = logging.getLogger("MY IO")
    BOT_NAME = "robotarium-bot"
    ALANA_REQUEST_TEXT_FIELD = "current_state.state.nlu.processed_text"
    ALANA_REQUEST_LAST_BOT_FIELD = "current_state.state.last_bot"
    ALANA_REQUEST_USER_ID_FIELD = "current_state.user_id"

    def name(a) -> Text:
        """Name of your custom channel."""
        return "myio"
    def blueprint(
            self, on_new_message: Callable[[UserMessage], Awaitable[None]]
    ) -> Blueprint:
        custom_webhook = Blueprint(
            "custom_webhook_{}".format(type(self).__name__),
            inspect.getmodule(self).__name__,
        )

        @custom_webhook.route("/", methods=["GET"])
        async def health(request: Request) -> HTTPResponse:
            return response.json({"status": "ok"})

        @custom_webhook.route("/webhook", methods=["POST"])
        async def receive(request: Request) -> HTTPResponse:
            """ #### Processes incoming requests and forwards them to the active rasa bot
            """

            # sender_id = request.json.get("sender")  # method to get sender_id
            # text = request.json.get("text")  # method to fetch text
            # input_channel = self.name()  # method to fetch input channel
            # input_channel = "myio"  # method to fetch input channel
            # metadata = self.get_metadata(request)  # method to get metadata

            # collector = CollectingOutputChannel()
            # f = open("alana_request_current_state.txt", "w")
            # f.write(json.dumps(request.json.get("current_state")))
            # f.close()
            # request_data = request.load_json()
            # sender_id = request_data.get("current_state").get("user_id")
            # text = request_data.json.get("current_state.state.nlu.annotations.processed_text")
            # text = request_data.get("current_state").get("state").get("nlu").get("processed_text")
            # print(text)
            # print(sender_id)
            # print(collector)
            # text = request_data.get("current_state.state.nlu.annotations.processed_text")
            # last_bot = request_data.get("current_state.state.last_bot")

            # include exception handling
            request_data = request.load_json()
            sender_id = request_data.get("current_state").get("user_id")
            # sender_id = request.json.get(MyIO.ALANA_REQUEST_USER_ID_FIELD)
            input_channel = self.name()  # method to fetch input channel
            metadata = self.get_metadata(request)
            collector = CollectingOutputChannel() # collects the response messages from rasa
            # text = request.json.get(MyIO.ALANA_REQUEST_TEXT_FIELD) or ''
            text = request_data.get("current_state").get("state").get("nlu").get("processed_text")
            last_bot = request.json.get(MyIO.ALANA_REQUEST_LAST_BOT_FIELD)
            self.logger.info("Text received from Alana: " + text + " " + sender_id)
            # include exception handling
            await on_new_message(
                UserMessage(
                    text,
                    collector,
                    sender_id,
                    input_channel=input_channel,
                    metadata=metadata,
                )
            )
            alana_response = convert_rasa_response_to_alana_response(
                bot_name=MyIO.BOT_NAME,
                rasa_response=collector.messages
            )
            self.logger.info("Response sent back to alana: " + json.dumps(alana_response.toJSON()))
            return response.json([alana_response.toJSON()])

        def convert_rasa_response_to_alana_response(bot_name: str, rasa_response: list) -> abstract_classes.Response:
            """ Converts Rasa response to Alana Response as prescribed in abstract_classes.Response
            """
            alana_response = abstract_classes.Response({
                'bot_name': bot_name,
                'bot_version': '0.1'
            })
            if not rasa_response or not rasa_response.count:
                alana_response.result = ''
            else:
                overall_message = ''
                for message in rasa_response:
                    # print(message.get("text"))
                    overall_message += ' ' + message.get("text")
                alana_response.result = overall_message
            return alana_response
        return custom_webhook