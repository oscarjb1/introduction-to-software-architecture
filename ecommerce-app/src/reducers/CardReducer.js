import {
    ADD_TO_CARD,
    REMOVE_TO_CARD,
    CLEAR_CARD
  } from './const'
  
  import update from 'react-addons-update'
  
  const initialState = {
    products: [
        {
          id: 1,
          name: "Product A",
          price: 100
        },
        {
          id: 2,
          name: "Product B",
          price: 200
        }
     ]
  }
  
  export const reducer = (state = initialState, action) => {
    switch (action.type) {
      case ADD_TO_CARD:
        return update(state, {
            products: {$splice: [[0,0,action.product]]}
        })
      
      case REMOVE_TO_CARD:
        let index = state.products.map(prod => prod.id).indexOf(action.product.id)
        return update(state, {
            products: {$splice: [[index,1]]}
        })

      case CLEAR_CARD:
        return update(state, {
          products: {$set: []}
        })
      default:
        return state
    }
  }
  
  export default reducer
  