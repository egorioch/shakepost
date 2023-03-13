<template>
  <div>
    <input type="text" placeholder="write something" v-model="text"/>
    <input type="button" value="save" style="position: relative" @click="save">
  </div>
</template>

<script>
import {sendMessage} from "../../util/ws";

export default {
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
  methods: {
    save: function () {
      sendMessage({id: this.id, text: this.text});
      this.id = ''
      this.text = ''

      /*let message = {text: this.text};

      if (this.id) {
        this.$resource('/message{/id}').update({id: this.id}, message).then(result =>
            result.json().then(data => {
              console.log("dataUpdate: " + JSON.stringify(data));
              let index = this.messages.filter(message => message.id === data.id);
              this.messages.splice(index, 1, data);
              this.id = "";
              this.text = "";
            })
        )
      } else {
        this.$resource('/message{/id}').save({}, message).then(result =>
            result.json().then(data => {
              this.messages.push(data);
              this.text = "";
            })
        )
      }*/

    }
  }
}
</script>