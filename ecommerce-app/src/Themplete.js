import React from 'react'
import config from './config'
import APIInvoker from './utils/APIInvoker'
import Orders from './Orders'
import OrderForm from './OrderForm'
import {  Route } from "react-router"
import './App.css'
import { Link } from 'react-router-dom'
import ProductList from './ProductList'

export default class Themplete extends React.Component{

    constructor(args){
        super(args)
        this.state = {
            login: false
        }
    }

    componentDidMount(){
        const urlParams = new URLSearchParams(window.location.search);
        const newToken = urlParams.get('token');
        if(newToken){
            window.localStorage.setItem("token", newToken)
            window.location = '/'
        }

        let token = window.localStorage.getItem("token")
        if(!token){
            window.location = config.sso
        }

        APIInvoker.invokeGET(`/security/token/validate?token=${token}`,response => {
            console.log(response)
            this.setState({
                login: true
            })
        }, error => {
            window.location = config.sso
        } )        
    }

    logout(){
        window.localStorage.removeItem("token")
        window.location = '/'
    }


    render(){
        return(
            <div>
                <nav className="navbar navbar-dark bg-dark">
                    <Link className="navbar-brand mb-0 h1" to={"/"}>
                        <img src="/obb-logo-small.png" width="30" height="30" className="d-inline-block align-top" alt=""/>
                         <span> E-Commerce </span>
                    </Link>

                    <button onClick={() => this.logout()} className="btn btn-sm btn-outline-secondary" type="button">Logout</button>
                </nav>
            
                <main>
                    <div className="container">
                        {this.state.login
                            ? <React.Fragment>
                                <Route exact path="/" component={ProductList}/>
                                <Route exact path="/orders" component={Orders}/>
                                <Route exact path="/orders/:orderID" component={OrderForm}/>
                            </React.Fragment>
                            
                            : <p></p>
                        }
                    </div>
                </main>
            </div>
            
            
        )
    }
}