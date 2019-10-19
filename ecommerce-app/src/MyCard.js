import React from 'react'
import { connect } from 'react-redux';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import "react-tabs/style/react-tabs.css";
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';
import APIInvoker from './utils/APIInvoker'
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
import { clearCard } from './reducers/actions'
import {Choose, When, Otherwise} from 'react-control-statements'
import {Link} from 'react-router-dom'

class MyCard extends React.Component{

    constructor(args){
        super(args)
        this.state = {
            paymentMethod: "CREDIT_CARD",
            dialogVisible: false,
            number: '',
            name: '',
            expiry: '',
            cvc: '',
            focused: ''
        }
    }

    componentDidMount(){
        if(this.props.card.length === 0){
            alert('Empty card')
            window.location = '/'
        }
    }

    handleInput(e){
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    focus(focus){
        this.setState({
            focused: focus
        })
    }

    createNewOrder(){

        let lines = this.props.card.map(product => { return {
            productId: product.id,
            quantity: 1
        }})

        let request = {
            //customerName: this.props.user.username,
            //customerEmail: this.props.user.email,
            paymentMethod: this.state.paymentMethod,
            card: {
              name: this.state.name,
              number: this.state.number,
              expiry: this.state.expiry,
              cvc: this.state.cvc
            },
            orderLines: lines
          }

        APIInvoker.invokePOST('/crm/orders', request, response => {
            this.setState({
                order: response.body,
                dialogVisible: true
            })
            this.props.clearCard()
        }, error => {
            alert(error.message)
        })
    }

    hideDialog(){
        this.setState({
            dialogVisible: false
        })
    }

    goToOrder(){
        window.location = `/order/${this.state.order.id}`
    }

    changePaymentMethod(paymentMethod){
        this.setState({
            paymentMethod: paymentMethod
        })
    }

    render(){
        return(
            <div>
                <h3>Revisa tu pedido y realizo el pago</h3>
                <br/>
                <div style={{backgroundColor: 'white'}}>
                    <table className="table" >
                        <thead className="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Product</th>
                                <th scope="col">Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.props.card.map((product, index) => <tr key={index}>
                                <th scope="row">{index+1}</th>
                                <th scope="row">{product.name}</th>
                                <th scope="row">{product.price}</th>
                            </tr>)}
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colSpan="2"><p style={{textAlign: 'right'}}>Total</p></td>
                                <td>${this.props.card.reduce((last, current) => {return last + current.price}, 0)}</td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <br/>
                <h3>Seleccione forma de pago</h3>
                <div style={{textAlign: 'right'}}>
                    <Tabs onSelect={index => {this.changePaymentMethod(index === 0 ? 'CREDIT_CARD': 'DEPOSIT')}}>
                        <TabList>
                            <Tab>Tarjeta de crédito</Tab>
                            <Tab>Depósito bancario</Tab>
                        </TabList>

                        <TabPanel>
                            <br/>
                            <p className="text text-dark">Introduzca los datos de su tarjeta de crédito</p>    
                            <div className="row">
                                <div className="col-sm-6 col-md-4" style={{textAlign: 'right'}}>
                                    
                                    <Cards
                                        number={this.state.number}
                                        name={this.state.name}
                                        expiry={this.state.expiry}
                                        cvc={this.state.cvc}
                                        focused={this.state.focused}
                                    />
                                </div>
                                <div className="col-md-6 col-md-8">
                                    <form>
                                        <div className="form-group">
                                            <input onChange={this.handleInput.bind(this)} onFocus={(e) => {this.focus('number')}} type="text" maxLength="16"  className="form-control form-control-lg" id="number" name="number" placeholder="Número de tarjeta"/>
                                        </div>
                                        <div className="form-group">
                                            <input onChange={this.handleInput.bind(this)} onFocus={(e) => {this.focus('name')}} type="text" maxLength="30" className="form-control form-control-lg" id="name" name="name" placeholder="Nombre del cliente"/>
                                        </div>
                                        <div className="form-row">
                                            <div className="form-group col-md-9">
                                                <input onChange={this.handleInput.bind(this)} onFocus={(e) => {this.focus('expiry')}} type="text" className="form-control form-control-lg" maxLength="4" id="expiry" name="expiry" placeholder="Fecha vencimiento"/>
                                            </div>
                                            <div className="form-group col-md-3">
                                                <input onChange={this.handleInput.bind(this)} maxLength="4" onFocus={(e) => {this.focus('cvc')}} type="text" className="form-control form-control-lg" id="cvc" name="cvc" placeholder="CVC"/>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <button onClick={() => this.createNewOrder('CREDIT_CARD')} className="btn btn-success">  Pagar  </button>
                            
                        </TabPanel>
                        <TabPanel>
                            <br/>
                            <p className="text text-dark">Este método de pago le permite realizar el pago directamente en las ventanillas del banco o por medio de las diferentes tiendas de conveniencia. <span className="text text-danger">El sistema le generá un número de orden que tendrá que poner como referencia en su pago para que el sistema pueda identificar su pago.</span></p>    

                            

                            <button onClick={() => this.createNewOrder('CREDIT_CARD')} className="btn btn-success">  Pagar  </button>
                        </TabPanel>
                    </Tabs>
                    
                </div>

                <Rodal  width={500} showCloseButton={false} closeOnEsc={false} visible={this.state.dialogVisible} onClose={this.hideDialog.bind(this)}>
                    
                    <p style={{fontSize: '22px', fontWeight: 'bold'}} className="text text-success">Pedido realizado con éxito</p>

                    <Choose>
                        <When condition={this.state.paymentMethod === 'CREDIT_CARD'} >
                            <p>Tu pedido ha sido registrado con éxito, un email llegará a tu correo con los detalles de tu compra</p>
                        </When>
                        <Otherwise>
                            <p>Tu pedido ha sido registrado con éxito. Recuerda que es importate referenciar el pago con el siguiente número número de pedido.</p>
                        </Otherwise>
                    </Choose>
                    <p>Pedido No: {this.state.order ? this.state.order.refNumber : ''}</p>
                    <div style={{textAlign: 'right'}}>
                        <Choose>
                            <When condition={this.state.order && this.state.order.queued}>
                                <Link to={"/"} className="btn btn-success" style={{marginRight: '10px'}}>Regresar</Link>
                            </When>
                            <Otherwise>
                                <button onClick={() => this.goToOrder()} className="btn btn-success" style={{marginRight: '10px'}}>Ver mi compra</button>
                            </Otherwise>
                        </Choose>
                        
                    </div>
                    
                </Rodal>
                
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        card: state.card.products,
        user: state.user.user
    }
}

export default connect(mapStateToProps, {clearCard})(MyCard)