<template>
  <v-app app>
    <v-app-bar app>
      <v-toolbar-title>Shakepost</v-toolbar-title>
      <v-spacer></v-spacer>
      <span v-if="profile">{{ profile.name }}</span>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-content>
      <v-container v-if="!profile">Необходимо авторизоваться через
        <a href="/login">Google</a>
      </v-container>
      <v-container v-else>
        <message-list v-bind:messages="messages"></message-list>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import MessageList from "../components/messages/MessageList.vue";
import {addHandler} from "../util/ws";

export default {
  components: {MessageList},
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