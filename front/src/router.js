import Vue from 'vue'
import Router from 'vue-router'
import UserList from '@/views/UserList.vue'
import ShowUserCra from '@/views/UserCra.vue' // TODO rename component
import AddProject from '@/views/AddProject.vue'
import AddUser from '@/views/AddUser.vue'
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
      props: true,
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: ShowUserCra
    },
    {
      path: '/formProject',
      props: true,
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: AddProject
    },
    {
      path: '/formUser',
      props: true,
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: AddUser
    },
    {
      path: '/timeSpent',
      props: true,
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: TimeSpent
    },
    {
      path: '*',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: NotFound
    },
  ]
})
