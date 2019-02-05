import React from 'react'
import APIInvoker from './utils/APIInvoker';
import ProductCard from './ProductCard'
import { connect } from 'react-redux';
import { Link } from 'react-router-dom'

class ProductList extends React.Component{

    constructor(args){
        super(args)
        this.state = {
            load: false,
            products: []
        }
    }

    goToCard(){

    }

    componentWillMount(){
        APIInvoker.invokeGET("/crm/products", response => {
            this.setState({
                load: true,
                products: response.body
            })
        }, error => {
            alert(error.message)
        })
    }

    render(){
        return(
            <div>   
                <div className="row">
                    {this.state.products.map(item => <div className="col-sm-6" key={item.id}><ProductCard  product = {item}/></div>)}
                </div>
                

                {this.props.card.length === 0
                    ? <button className="goToCard btn btn-warning disabled">Empty card</button>
                    : <Link to={"/my-card"} className="goToCard btn btn-warning">Finish buy ({this.props.card.length} products)</Link>
                }
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        card: state.card.products
    }
}

export default connect(mapStateToProps, {})(ProductList)