import React from 'react'
import ReactTable from "react-table";
import "react-table/react-table.css";
import APIInvoker from './utils/APIInvoker'
import { Link } from 'react-router-dom'


export default class Orders extends React.Component{

    constructor(args){
        super(args)
        this.state = {
            load: false,
            orders: []
        }
    }

    componentWillMount(){
        APIInvoker.invokeGET('/crm/orders', response => {
            console.log("response =>", response)
            this.setState({
                load: true,
                orders: response.body
            })
        }, error => {
            alert(error.message)
        })
    }

    render(){
        return(
            <div>
                <div className="controls">
                    <Link className="btn btn-sm btn-dark" to={"/orders/new"} >Nuevo</Link>
                </div>
                <ReactTable
                    data={this.state.orders}
                    columns={[
                        {
                            Header: "ID",
                            accessor: "id",
                            filterable: true
                        },

                        {
                            Header: "User",
                            accessor: "customerName",
                            filterable: true,
                        },
                        {
                            Header: "Ref Number",
                            accessor: "refNumber",
                            filterable: true,
                        },
                        {
                            Header: "Total",
                            accessor: "total",
                            filterable: true,
                        },
                        {
                            Header: "Date",
                            accessor: "registDate",
                            filterable: true,
                        },
                        
                    ]}
                    defaultSorted={[
                        {
                        id: "age",
                        desc: true
                        }
                    ]}
                    defaultPageSize={10}
                    className="-striped -highlight"
                    />
            </div>
        )
    }
}