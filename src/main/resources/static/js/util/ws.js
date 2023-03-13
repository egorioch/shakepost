import SockJS from 'sockjs-client'
import {Stomp} from "@stomp/stompjs"

//это -- список, содержащий в себе функции обработки сообщений, пришедших с сервера
//он создается лишь однажды -- когда страница загружается
const handlers = []

let stompClient = null;

export function connect() {
  let socket = new SockJS('/message-socket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    console.log('Connected: ' + frame);
    //подписываемся на пункт назначения
    //именно тут мы получаем ответ от сервера по этому "эндпоинту"
    stompClient.subscribe('/topic/activity', message => {
      //всякий раз, когда мы будем получать ответ от сервера,
      //мы будем пробегать "обработчики" и добавлять в хэндлер пришедший объект
      //с сервера мы получаем полноценный объект, у которого есть поля id, text.
      handlers.forEach(handler => {
        console.log("message.body, идущий в handler: " + JSON.parse(message.body))
        handler(JSON.parse(message.body))
      })
    });
  });
}

export function addHandler(handler) {
  handlers.push(handler);
  console.log("список всех handlers: ");
  handlers.forEach(hand => console.log("handler: " + hand));
}

export function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }

  console.log("Disconnected");
}

//отправляем сообщение на сервер
export function sendMessage(message) {
  stompClient.send("/app/changeMessage", {}, JSON.stringify(message));
}