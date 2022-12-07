import React from 'react';
import { filter } from 'lodash';
import { sentenceCase } from 'change-case';
import { useState } from 'react';
import PropTypes from 'prop-types';
// @mui
import {
  Card,
  Table,
  TableRow,
  TableBody,
  TableCell,
  Container,
  IconButton,
  TableContainer,
  TablePagination,
  Tooltip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button,
} from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import DeleteIcon from '@mui/icons-material/Delete';
// components
import Label from '../label';
import Scrollbar from './scrollbar';
// sections
import { UserListHead } from './user';

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: 'preApprovalForm', label: 'PreApproval Form', alignRight: false },
  { id: 'type', label: 'Type', alignRight: false },
  { id: 'status', label: 'Status', alignRight: true },
];

// ----------------------------------------------------------------------

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function applySortFilter(array, comparator, query) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  if (query) {
    return filter(array, _user => _user.name.toLowerCase().indexOf(query.toLowerCase()) !== -1);
  }
  return stabilizedThis.map(el => el[0]);
}

const PreApprovalsTableForStudents = ({ preApprovalForms }) => {
  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const handleOpenApplication = id => {
    console.log("id: ", id);
  };

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setPage(0);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const filteredUsers = applySortFilter(preApprovalForms, getComparator(order, orderBy), null);

  const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - preApprovalForms.length) : 0;

  return (
    <>
      <Container>
        <Card>
          <Scrollbar>
            <TableContainer sx={{ minWidth: 500 }}>
              <Table>
                <UserListHead
                  order={order}
                  orderBy={orderBy}
                  headLabel={TABLE_HEAD}
                  onRequestSort={handleRequestSort}
                />
                <TableBody>
                  {filteredUsers.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row, index) => {
                    const { id, type, status } = row;

                    return (
                      <TableRow hover key={id} tabIndex={-1} role="checkbox" >
                        <TableCell padding="checkbox"></TableCell>

                        <TableCell align='center' component="th" scope="row" padding="none">{`PreApproval Form ${index+1}`}</TableCell>

                        <TableCell align='center' component="th" scope="row" padding="none">{type}</TableCell>

                        <TableCell align="center">
                          <Label color={(status === 'waiting' && 'warning') || (status === 'rejected' && 'error') || 'success'}>{sentenceCase(status)}</Label>
                        </TableCell>

                        <TableCell align="right">
                          <Tooltip describeChild title="Open request details">
                            <IconButton size="large" color="inherit" onClick={() => handleOpenApplication(id) }>
                              <DescriptionIcon />
                            </IconButton>
                          </Tooltip>
                          <Tooltip describeChild title="Delete request">
                            <IconButton size="large" color="error" onClick={() => handleClickOpen() }>
                              <DeleteIcon />
                            </IconButton>
                          </Tooltip>
                          <Dialog
                            open={open}
                            onClose={handleClose}
                            aria-labelledby="alert-dialog-title"
                            aria-describedby="alert-dialog-description"
                          >
                            <DialogTitle id="alert-dialog-title">
                              {"Remove Request"}
                            </DialogTitle>
                            <DialogContent>
                              <DialogContentText id="alert-dialog-description">
                                You can not take it back if you remove a request. Do you want to remove?
                              </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                              <Button onClick={handleClose}>No</Button>
                              <Button onClick={handleClose} autoFocus>
                                Yes
                              </Button>
                            </DialogActions>
                          </Dialog>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                  {emptyRows > 0 && (
                    <TableRow style={{ height: 53 * emptyRows }}>
                      <TableCell colSpan={6} />
                    </TableRow>
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </Scrollbar>

          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={preApprovalForms.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Card>
      </Container>
    </>
  );
};

PreApprovalsTableForStudents.propTypes = {
    preApprovalForms: PropTypes.array,
};
  
PreApprovalsTableForStudents.defaultProps = {
    preApprovalForms: [],
};


export default PreApprovalsTableForStudents;
