import React from 'react'
import APIInvoker from './utils/APIInvoker';
import {Choose, Otherwise, When, If} from 'react-control-statements'

export default class OrderForm extends React.Component{

    constructor(args){
        super(args)
        this.state = {
            load: false,
            order:{
                payment: {},
                orderLines:[]
            }
        }
    }

    componentDidMount(){
        let orderID =this.props.match.params.orderID
        APIInvoker.invokeGET(`/crm/orders/${orderID}`, response => {
            this.setState({
                load: true,
                order: response.body
            })
        }, error => {
            alert(error.message)
        })
    }

    render(){
        return(
            <div>
                <Choose>
                    <When condition={this.state.load}>
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
                                    {this.state.order.orderLines.map((line, index) => <tr key={index}>
                                        <th scope="row">{index+1}</th>
                                        <th scope="row">{line.product.name}</th>
                                        <th scope="row">{line.product.price}</th>
                                    </tr>)}
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colSpan="2"><p style={{textAlign: 'right'}}>Total</p></td>
                                        <td>${this.state.order.orderLines.reduce((last, current) => {return last + current.product.price}, 0)}</td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </When>
                    <Otherwise>
                        <p>hola</p>
                    </Otherwise>
                </Choose>
                <h3>Datos de la compra</h3>
                <br/>
                <form>
                    <div className="form-group row">
                        <label for="staticEmail" className="col-sm-2 col-form-label">Usuario</label>
                        <div className="col-sm-10">
                        <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.customerName}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label for="staticEmail" className="col-sm-2 col-form-label">User Email</label>
                        <div className="col-sm-10">
                        <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.customerEmail}/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label for="staticEmail" className="col-sm-2 col-form-label">Número de orden</label>
                        <div className="col-sm-10">
                        <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.refNumber}/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label for="staticEmail" className="col-sm-2 col-form-label">Fecha de creación</label>
                        <div className="col-sm-10">
                        <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.registDate}/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label for="staticEmail" className="col-sm-2 col-form-label">Estatus</label>
                        <div className="col-sm-10">
                        <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.status}/>
                        </div>
                    </div>

                    <If condition={this.state.order.payment} >
                        <h3>Datos del pago</h3>

                        <div className="form-group row">
                            <label for="staticEmail" className="col-sm-2 col-form-label">Fecha de pago</label>
                            <div className="col-sm-10">
                            <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.payment.paydate}/>
                            </div>
                        </div>

                        <div className="form-group row">
                            <label for="staticEmail" className="col-sm-2 col-form-label">Método de pago</label>
                            <div className="col-sm-10">
                            <input type="text" readonly className="form-control-plaintext" id="staticEmail" value={this.state.order.payment.paymentMethod === 'CREDIT_CARD'? 'Tarjeta de crédito': 'Depósito'}/>
                            </div>
                        </div>

                    </If>
                    
                    
                </form>
                <br/>
            </div>
        )
    }
}