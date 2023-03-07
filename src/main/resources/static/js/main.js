let messageApi = Vue.resource('/message{/id}');

Vue.component('message-form', {
  props: ['messages', 'messageAttr'],
  data: function () {
    return {
      id: '',
      text: ''
    }
  },
  watch: {
    messageAttr: function (newVal, oldVal) {
      console.log("oldId: " + this.id + ", newId: " + newVal.id);
      this.id = newVal.id;
      this.text = newVal.text
    }
  },
  template: '<div>' +
    '<input type="text" placeholder="write something" v-model="text" />' +
    '<input type="button" value="save" style="position: relative" @click="save">' +
    '</div>',
  methods: {
    save: function () {
      let message = {text: this.text};

      if (this.id) {
        messageApi.update({id: this.id}, message).then(result =>
          result.json().then(data => {
            console.log("dataUpdate: " + JSON.stringify(data));
            let index = this.messages.filter(message => message.id === data.id);
            this.messages.splice(index, 1, data);
            this.id = "";
            this.text = "";
          })
        )
      } else {
        messageApi.save({}, message).then(result =>
          result.json().then(data => {
            this.messages.push(data);
            this.text = "";
          })
        )
      }

    }
  }
});

Vue.component('message-row', {
  props: ['message', 'messages', 'editMethod'],
  template: '<div>' +
    '<i> ({{message.id}})</i> {{message.text}}' +
    '<span>' +
    '<input type="button" value="edit" @click="edit">' +
    '<input type="button" value="X" @click="deleteMessage">' +
    '</span>' +
    '</div>',
  methods: {
    edit: function () {
      this.editMethod(this.message);
    },
    deleteMessage: function () {
      messageApi.delete({id: this.message.id}).then(result => {
        if (result.ok) {
          this.messages.splice(this.messages.indexOf(this.message), 1);
        }
      })
    }
  }
});

Vue.component('message-list', {
  props: ['messages'],
  data: function () {
    return {
      message: null
    }
  },
  template:
    '<div style="position: relative; width: 300px">' +
    '<message-form :messageAttr="message" :messages="messages" />' +
    '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
    ':messages="messages" :editMethod="editMethod"/>' +
    '</div>',
  created: function () {
    messageApi.get().then(result =>
      result.json().then(data =>
        data.forEach(message => this.messages.push(message)))
    )
  },
  methods: {
    editMethod: function (message) {
      this.message = message;
    }
  }
});

var app = new Vue({
  el: '#app',
  template: '<div><message-list v-bind:messages="messages"></message-list></div>',
  data: {
    messages: []
  },

});

