# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

# from typing import Any, Text, Dict, List
#
# from rasa_sdk import Action, Tracker
# from rasa_sdk.executor import CollectingDispatcher
#
#
# class ActionHelloWorld(Action):
#
#     def name(self) -> Text:
#         return "action_hello_world"
#
#     def run(self, dispatcher: CollectingDispatcher,
#             tracker: Tracker,
#             domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
#
#         dispatcher.utter_message(text="Hello World!")
#
#         return []

from typing import Any, Text, Dict, List

from rasa_sdk.executor import CollectingDispatcher
from rasa_sdk import Action, Tracker

from rasa_sdk.events import AllSlotsReset

#-----------------------------------------------------------------------------------#

# custom action to reset all slots to empty in a form
class ActionResetSlots(Action):

    # defines the name of the custom action
    def name(self) -> Text:
        return "action_reset_slots"

    # defines what happens when the custom action is run
    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        return [AllSlotsReset()]

#-----------------------------------------------------------------------------------#

# custom action to write to file for coffee simulation
class ActionAppendFile(Action):
    
    # defines the name of the custom action
    def name(self) -> Text:
        return "action_append_file"

    # write to the file when the custom action is called
    def run(self, dispatcher, tracker, domain):
        coffee_size = tracker.get_slot(size)

        print("custom action worked: " + size)
#-----------------------------------------------------------------------------------#