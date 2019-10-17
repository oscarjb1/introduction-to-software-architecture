import React from 'react'
import config from './config'
import APIInvoker from './utils/APIInvoker'
import OrderForm from './OrderForm'
import {  Route } from "react-router"
import './App.css'
import { Link } from 'react-router-dom'
import ProductList from './ProductList'
import { Choose, When, Otherwise } from 'react-control-statements'
import MyCard from './MyCard'
import {connect} from 'react-redux'
import {setUser} from './reducers/actions'
import Orders from './Orders';

class Themplete extends React.Component{

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
            this.setState({
                login: true
            })
            this.props.setUser(response.body)
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
                <Choose>
                    <When condition={this.state.login} >
                        <nav className="navbar navbar-dark bg-dark">
                            <Link className="navbar-brand mb-0 h1" to={"/"}>
                                <img src="/obb-logo-small.png" width="30" height="30" className="d-inline-block align-top" alt=""/>
                                <span> E-Commerce </span>
                            </Link>


                            <div>
                                <Link to={"/orders"} className="btn btn-sm btn-outline text text-white" style={{marginRight: '10px'}}>My orders</Link>
                                <button onClick={() => this.logout()} className="btn btn-sm btn-outline-secondary" type="button">Logout</button>
                            </div>
                            
                        </nav>
                    
                        <main>
                            <div className="container">
                                <Choose>
                                    <When condition={this.state.login}>
                                        <React.Fragment>
                                            <Route exact path="/" component={ProductList}/>
                                            <Route exact path="/my-card" component={MyCard}/>
                                            <Route exact path="/order/:orderID" component={OrderForm}/>
                                            <Route exact path="/orders" component={Orders}/>
                                        </React.Fragment>
                                    </When>
                                    <Otherwise>
                                    <p></p>
                                    </Otherwise>
                                </Choose>
                            </div>
                        </main>
                    </When>
                    <Otherwise>
                        <p>Login needed</p>
                        <button className="btn btn-success" onClick={() => {window.location = config.sso}}>Go to login page</button>
                    </Otherwise>
                </Choose>
                
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        card: state.card.products
    }
}

export default connect(mapStateToProps, {setUser})(Themplete)