import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import {addToCard, removeToCard} from './reducers/actions'
import {connect} from 'react-redux'
import { Choose, Otherwise, When} from 'react-control-statements'

const styles = theme => ({
  card: {
    display: 'flex',
    margin: '20px'
  },
  details: {
    display: 'flex',
    flexDirection: 'column',
  },
  content: {
    flex: '1 0 auto',
  },
  cover: {
    width: 200,
    height: 300
  },
  controls: {
    display: 'flex',
    alignItems: 'center',
    paddingLeft: theme.spacing.unit,
    paddingBottom: theme.spacing.unit,
  },
  playIcon: {
    height: 38,
    width: 38,
  },
});

function MediaControlCard(props) {
  const { classes, product, card, removeToCard, addToCard } = props;

  return (
    <Card className={classes.card}>
        <CardMedia
        className={classes.cover}
        image={`${product.image}`}
        //image="https://m.media-amazon.com/images/M/MV5BYThjYzcyYzItNTVjNy00NDk0LTgwMWQtYjMwNmNlNWJhMzMyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
        title="Live from space album cover"  />
        <div className={classes.details}>
            <CardContent className={classes.content}>
            <Typography component="h5" variant="h5">
                {product.name}
            </Typography>
            <Typography variant="subtitle1" color="textSecondary">
                <span className="text text-success">${product.price}</span>
            </Typography>
            </CardContent>
            
            <div className={classes.controls}>
                <Choose>
                    <When condition={ card.map(addItem => addItem.id).indexOf(product.id) !== -1}>
                        <button onClick={() => removeToCard(product)} className="btn btn-danger">Remove from the card</button>
                    </When>
                    <Otherwise> 
                        <button onClick={() => addToCard(product)} className="btn btn-success">Add to card</button>
                    </Otherwise>
                </Choose>
            </div>
        </div>
    </Card>
  );
}

const mapStateToProps = (state) => {
    return {
        card: state.card.products
    }
}

MediaControlCard.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
  product: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(connect(mapStateToProps, {addToCard, removeToCard})(MediaControlCard));