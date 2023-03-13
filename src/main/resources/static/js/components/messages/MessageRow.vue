<template>
  <v-card class="spacing-playground my-2">
    <v-card-text primary-text>
      <i> ({{ message.id }})</i>
      {{ message.text }}
    </v-card-text>
    <v-card-actions>
      <v-btn small text @click="edit" rounded>Edit</v-btn>
      <v-btn small icon @click="del">
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: ['message', 'messages', 'editMethod', 'deleteMessage'],
  methods: {
    edit: function () {
      this.editMethod(this.message);
    },
    del: function () {
      this.deleteMessage(this.message);
    }
  }
}
</script>