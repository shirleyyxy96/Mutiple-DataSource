import Vue from 'vue';
import Router from 'vue-router';
import Index from '@/views/Index';
import User from '@/views/User';
import Test from '@/views/Test';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [{
    path: '/',
    name: 'Index',
    component: Index,
  },
  {
    path: '/user/:id',
    name: 'UserDetail',
    component: User,
  },
  {
    path: '/user',
    name: 'User',
    component: User,
  },
  {
    path: '/test',
    name: 'Test',
    component: Test,
  },
  ],
});
