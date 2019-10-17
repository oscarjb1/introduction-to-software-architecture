import React from 'react'

import {  Route} from "react-router"
import { BrowserRouter as Router } from "react-router-dom"
import Themplete from './Themplete'
import { createStore, applyMiddleware } from 'redux'
import { Provider } from 'react-redux'
import thunk from 'redux-thunk'
import reducers from './reducers'

const middleware = [ thunk ];
if (process.env.NODE_ENV !== 'production') {
  //middleware.push(createLogger());
}

export const store = createStore(
  reducers,
  applyMiddleware(...middleware)
)

const App = () => {
  return (
    <Provider store={ store }>
      <Router>
        <Router>
          <Route path="/" component= {Themplete}>
          </Route>
        </Router>
      </Router>
    </Provider>
  )
}

export default App;