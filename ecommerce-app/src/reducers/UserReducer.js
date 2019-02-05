import {
    SET_USER
  } from './const'
  
  import update from 'react-addons-update'
  
  const initialState = {
    user: {}
  }
  
  export const reducer = (state = initialState, action) => {
    switch (action.type) {
      case SET_USER:
        return update(state, {
            user: {$set: action.user}
        })
      default:
        return state
    }
  }
  
  export default reducer
  