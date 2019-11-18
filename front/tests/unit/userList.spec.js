import { shallowMount } from '@vue/test-utils'
import UserList from '@/views/UserList.vue'

const factory = (values = {}) => {
  return shallowMount(UserList, {
    data () {
      return {
        ...values
      }
    }
  })
}
describe('MyComponent', () => {
  // Inspecter l'objet d'options du composant
  it('a le hook `mounted`', () => {
    expect(typeof UserList.mounted).toBe('function')
  }),
  it('vérification de la mise à jour de "watch"', () => {
    expect(typeof UserList.mounted).toBe('function')
  })
});
