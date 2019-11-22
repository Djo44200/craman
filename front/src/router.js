import Vue from 'vue'
import Router from 'vue-router'
import UserList from '@/views/UserList.vue'
import ShowUserCra from '@/views/UserCra.vue'
import FormProject from '@/views/FormProject.vue'
import FormUser from '@/views/FormUser.vue'
import TimeSpent from '@/views/TimeSpent.vue'
import NotFound from '@/views/404.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'userList',
      component: UserList,
    },
    {
      path: '/user/:id',
      name: 'userCra',
      props: true,
      component: ShowUserCra
    },
    {
      path: '/formProject',
      name: 'formProject',
      props: true,
      component: FormProject
    },
    {
      path: '/formUser',
      name: 'formUser',
      props: true,
      component: FormUser
    },
    {
      path: '/timeSpent',
      name: 'timeSpent',
      props: true,
      component: TimeSpent
    },
    {
      path: '*',
      name: 'notFound',
      component: NotFound
    },
  ]
})
