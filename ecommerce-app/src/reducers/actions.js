import { ADD_TO_CARD, REMOVE_TO_CARD, SET_USER } from "./const";


export const addToCard = (product) => (dispatch, getState) => {
    dispatch({
        type: ADD_TO_CARD,
        product: product
    })    
}

export const removeToCard = (product) => (dispatch, getState) => {
    dispatch({
        type: REMOVE_TO_CARD,
        product: product
    })    
}

export const setUser = (user) => (dispatch, getState) => {
    dispatch({
        type: SET_USER,
        user: user
    })    
}
