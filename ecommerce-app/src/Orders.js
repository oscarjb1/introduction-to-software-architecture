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
                <br/>
                <h3>Todas tus órdenes</h3>
                <br/>
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
                        {
                            Header: "Actions",
                            accessor: "id",
                            filterable: true,
                            Cell: row => (<center><Link to={`/order/${row.value}`}><span style={{padding: '2px 20px'}} className="btn btn-sm btn-dark">Ver</span></Link></center>)
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