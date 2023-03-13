<template>
  <div>
    <div v-if="!profile">Необходимо авторизоваться через
      <a href="/login">Google</a>
    </div>
    <div v-else>
      <div>{{ profile.name }}&nbsp;<a href="/logout">Выйти</a></div>
      <message-list v-bind:messages="messages"></message-list>
    </div>
  </div>
</template>

<script>
import MessageList from "../components/messages/MessageList.vue";
import {addHandler} from "../util/ws";

export default {
  components: { MessageList },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  created() {
    //принимаем сообщение с сервера
    addHandler(data => {
      console.log('Добавление handler с data: ' + JSON.stringify(data));
      let index = this.messages.filter(message => message.id === data.id);
      if (index < -1) {
        this.messages.splice(index.id, 1, data);
      } else {
        this.messages.push(data);
      }
    })
  }
}
</script>