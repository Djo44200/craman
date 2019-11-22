import Vue from 'vue'

// import './styles/quasar.styl'
import 'quasar/dist/quasar.css';
import 'quasar/dist/quasar.ie.polyfills'
import '@quasar/extras/roboto-font/roboto-font.css'
import '@quasar/extras/material-icons/material-icons.css'
import '@quasar/extras/fontawesome-v5/fontawesome-v5.css'
import '@quasar/extras/ionicons-v4/ionicons-v4.css'
import '@quasar/extras/mdi-v3/mdi-v3.css'
import '@quasar/extras/eva-icons/eva-icons.css'
import {
  ClosePopup,
  Dialog,
  Notify,
  QAjaxBar,
  QAvatar,
  QBanner,
  QBtnToggle,
  QDialog,
  Quasar,
  QLayout,
  QHeader,
  QDrawer,
  QField,
  QForm,
  QImg,
  QMarkupTable,
  QInput,
  QPageContainer,
  QPage,
  QPopupEdit,
  QSelect,
  QToolbar,
  QToolbarTitle,
  QTable,
  QTh,
  QTr,
  QTd,
  QBtn,
  QIcon,
  QList,
  QItem,
  QItemSection,
  QItemLabel,
  Screen,
} from 'quasar'

Vue.use(Quasar, {
  config: {
    notify: { /* Notify defaults */ },
  },
  components: {
    ClosePopup,
    Dialog,
    Notify,
    QAjaxBar,
    QAvatar,
    QBanner,
    QBtnToggle,
    QDialog,
    QLayout,
    QMarkupTable,
    QInput,
    QHeader,
    QDrawer,
    QField,
    QForm,
    QImg,
    QPageContainer,
    QPage,
    QPopupEdit,
    QSelect,
    QToolbar,
    QToolbarTitle,
    QTable,
    QTh,
    QTr,
    QTd,
    QBtn,
    QIcon,
    QList,
    QItem,
    QItemSection,
    QItemLabel,
  },
  directives: {
  },
  plugins: {
    Dialog,
    Notify,
    Screen,
  }
 })
