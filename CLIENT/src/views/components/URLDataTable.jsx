import React, { PureComponent } from 'react';
import {
    DataTable,
    TableHeader,
    TableBody,
    TableRow,
    TableColumn,
    TablePagination
    } from 'react-md';

import { sort_by } from 'modules/arraysUtil.js';

export default class URLDataTable extends React.Component {

    constructor(props) {
        super(props);
        let headers = props.headers;
        this.state = {
            headers: headers,
            data: props.data,
            pageSize: props.pageSize,
            slicedData: props.data.slice(0, props.pageSize),
            order: {
                asc: false,
                field: 'created'
            }
        }
        this.handlePagination = this.handlePagination.bind(this);
    }

    handlePagination (start, rowsPerPage) {
        this.setState({ slicedData: this.state.data.slice(start, start + rowsPerPage) });
    }

    handleSort (field) {
        const ascending = !this.state.order.asc;
        let sortedItems = this.state.slicedData.slice();
        sortedItems = sortedItems.sort(sort_by(field.toLowerCase(), ascending, null));
        this.setState( {
            order: {
                asc: ascending,
                field: field
            },
            slicedData: sortedItems
        });
    };

    render() {
        const { slicedData, headers, pageSize } = this.state;
        const rowsPerPageLabel = this.props.mobile ? 'Rows' : 'Rows per page';
        return (
            <DataTable baseId="simple-pagination">
                <TableHeader>
                    <TableRow selectable={true}>
                        {headers.map(header =>
                            <TableColumn key={header.field} grow
                                sorted={this.state.order.field === header.field && this.state.order.asc}
                                role="button" onClick={() => this.handleSort(header.field)}>
                                {header.label}
                            </TableColumn>
                        )}
                    </TableRow>
                </TableHeader>
                <TableBody>
                {
                    slicedData.map( ({ shortened, source, owner, created }) => (
                          <TableRow key={created} selectable={true}>
                              <TableColumn>{created}</TableColumn>
                              <TableColumn>{shortened}</TableColumn>
                              <TableColumn>{source}</TableColumn>
                          </TableRow>
                      )
                    )
                }
                </TableBody>
                <TablePagination rows={this.state.data.length} rowsPerPageLabel={rowsPerPageLabel}
                    onPagination={this.handlePagination} />
            </DataTable>
        );
    }
}