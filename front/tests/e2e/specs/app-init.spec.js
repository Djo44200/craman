
describe('App init', ()=>{
  beforeEach(()=>{
    cy.visit('http://localhost/craman/#/')
  })
  it('Load users on page load',()=>{
    cy.server()
    cy.route('GET','http://localhost/craman-api/users')
    cy.get('td').should('have.length',2)
  })
  it('Display an error on failure',()=>{
    cy.server()
    cy.route({
      url: 'http://localhost/craman-api/user',
      method: 'GET',
      status: 500,
      response: {}
    })
    cy.get('tr').should('not.exist')
    cy.get('.error').should('be.visible')
  })
})
