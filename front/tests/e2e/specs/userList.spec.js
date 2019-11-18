// https://docs.cypress.io/api/introduction/api.html

describe('userList', () => {
  beforeEach(()=>{
    cy.visit('http://localhost/craman/#/')
  })
  it('Visits the app root url', () => {
    cy.contains('h5', 'Welcome')
    cy.focused().should('have.class', 'search')
  })
  it.only('accepts input', () => {
    const typeSearch = 'BODET'
    cy.get('input').type(typeSearch).should('have.value',typeSearch)
  })

})
