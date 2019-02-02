import React from 'react'
import APIInvoker from './utils/APIInvoker';
import ProductCard from './ProductCard'
import { connect } from 'react-redux';

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
                {this.state.products.map(item => <ProductCard key={item.id} product = {item} />)}
                {this.props.card.length == 0
                    ? <button className="goToCard btn btn-warning disabled">Empty card</button>
                    : <button onClick={() => this.goToCard()} className="goToCard btn btn-warning">Finish buy ({this.props.card.length} products)</button>
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