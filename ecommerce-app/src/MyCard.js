import React from 'react'
import { connect } from 'react-redux';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import "react-tabs/style/react-tabs.css";
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';

class MyCard extends React.Component{

    constructor(args){
        super(args)
        this.state = {
            number: '1234567890123456',
            name: 'oscar javier blancarte iturralde',
            expiry: '10/2010',
            cvc: '1234',
            focused: true
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

    focusCVC(focus){
        this.setState({
            focused: focus
        })
    }

    render(){
        return(
            <div>
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
                    <Tabs>
                        <TabList>
                            <Tab>Tarjeta de crédito</Tab>
                            <Tab>Depósito bancario</Tab>
                        </TabList>

                        <TabPanel>
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
                                        <div class="form-group">
                                            <input onChange={this.handleInput.bind(this)} type="text" maxlength="16"  class="form-control form-control-lg" id="number" name="number" placeholder="Número de tarjeta"/>
                                        </div>
                                        <div class="form-group">
                                            <input onChange={this.handleInput.bind(this)} type="text" maxlength="30" class="form-control form-control-lg" id="name" name="name" placeholder="Nombre del cliente"/>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-md-9">
                                                <input onChange={this.handleInput.bind(this)} type="text" class="form-control form-control-lg" maxlength="4" id="expiry" name="expiry" placeholder="Fecha vencimiento"/>
                                            </div>
                                            <div class="form-group col-md-3">
                                                <input onChange={this.handleInput.bind(this)} maxlength="4" onFocus={(e) => {this.focusCVC(true)}} onBlur ={(e) => {this.focusCVC(false)}} type="text" class="form-control form-control-lg" id="cvc" name="cvc" placeholder="CVC"/>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            
                            
                        </TabPanel>
                        <TabPanel>
                            <h2>Any content 2</h2>
                        </TabPanel>
                    </Tabs>
                    <button className="btn btn-success">  Pagar  </button>
                </div>
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

export default connect(mapStateToProps, {})(MyCard)