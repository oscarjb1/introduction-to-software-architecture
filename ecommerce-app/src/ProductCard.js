import React from 'react'
import {connect} from 'react-redux'
import { ADD_TO_CARD } from './reducers/const';
import APIInvoker from './utils/APIInvoker';
import {addToCard, removeToCard} from './reducers/actions'

class ProductCard extends React.Component{

    constructor(args){
        super(args)
    }

    addToCard(product){
        this.props.addToCard(product)
    }

    removeToCard(product){
        this.props.removeToCard(product)
    }

    render(){
        return(
            <div className="product-card">
                <h1>{this.props.product.name}</h1>
                <h2>${this.props.product.price}</h2>
                <div className="controls">
                    {
                        this.props.card.map(addItem => addItem.id).indexOf(this.props.product.id) !== -1
                        ? <button onClick={() => this.removeToCard(this.props.product)} className="btn btn-danger">Remove from the card</button>
                        : <button onClick={() => this.addToCard(this.props.product)} className="btn btn-success">Add to card</button>
                    }
                    
                </div>
            </div>
        )
    }

}

const mapStateToProps = (state) => {
    return {
        card: state.card.products
    }
}



export default connect(mapStateToProps, {addToCard, removeToCard})(ProductCard)