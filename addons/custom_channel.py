import asyncio
import inspect
from sanic import Sanic, Blueprint, response
from sanic.request import Request
from sanic.response import HTTPResponse
from typing import Text, Dict, Any, Optional, Callable, Awaitable, NoReturn

import rasa.utils.endpoints
from rasa.core.channels.channel import (
    InputChannel,
    CollectingOutputChannel,
    UserMessage,
)


class MyIO(InputChannel):
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
            sender_id = request.json.get("sender")  # method to get sender_id
            # text = request.json.get("text")  # method to fetch text
            input_channel = self.name()  # method to fetch input channel
            metadata = self.get_metadata(request)  # method to get metadata

            collector = CollectingOutputChannel()

            text = request.json.get("current_state.state.nlu.annotations.processed_text")
            # text = request_data.get("current_state.state.nlu.annotations.processed_text")
            # last_bot = request_data.get("current_state.state.last_bot")

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

            return response.json(collector.messages)

        return custom_webhook