import { combineReducers } from 'redux'
import CardReducer from './CardReducer'
import UserReducer from './UserReducer'

export default combineReducers({
  card: CardReducer,
  user: UserReducer
})
