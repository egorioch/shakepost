<template>
  <div style="position: relative; width: 300px">
    <message-form :messageAttr="message" :messages="messages"/>
    <message-row v-for="message in messages"
                 :key="message.id"
                 :message="message"
                 :messages="messages"
                 :editMethod="editMethod"
                 :deleteMessage="deleteMessage" />
  </div>
</template>

<script>
import MessageForm from "./MessageForm.vue";
import MessageRow from "./MessageRow.vue";

export default {
  components: {MessageForm, MessageRow},
  props: ['messages'],
  data: function () {
    return {
      message: null
    }
  },
  methods: {
    editMethod: function (message) {
      this.message = message;
    },
    deleteMessage(message) {
      this.$resource('/message{/id}').remove({id: message.id}).then(result => {
        if (result.ok) {
          this.messages.splice(this.messages.indexOf(message), 1)
        }
      })
    }
  }
}
</script>