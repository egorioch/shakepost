<template>
  <div>
    <i> ({{ message.id }})</i> {{ message.text }}
    <span>
  <input type="button" value="edit" @click="edit">
  <input type="button" value="X" @click="del">
  </span>
  </div>
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