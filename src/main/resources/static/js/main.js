import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import {connect} from "./util/ws";
import Vuetify from "vuetify";
import "@babel/polyfill";
import 'vuetify/dist/vuetify.min.css';

connect()

Vue.use(Vuetify)
Vue.use(VueResource)

new Vue({
  el: '#app',
  render: a => a(App),
  vuetify: new Vuetify({})
})


