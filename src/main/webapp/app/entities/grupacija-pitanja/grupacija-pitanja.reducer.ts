import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGrupacijaPitanja, defaultValue } from 'app/shared/model/grupacija-pitanja.model';

export const ACTION_TYPES = {
  SEARCH_GRUPACIJAPITANJAS: 'grupacijaPitanja/SEARCH_GRUPACIJAPITANJAS',
  FETCH_GRUPACIJAPITANJA_LIST: 'grupacijaPitanja/FETCH_GRUPACIJAPITANJA_LIST',
  FETCH_GRUPACIJAPITANJA: 'grupacijaPitanja/FETCH_GRUPACIJAPITANJA',
  CREATE_GRUPACIJAPITANJA: 'grupacijaPitanja/CREATE_GRUPACIJAPITANJA',
  UPDATE_GRUPACIJAPITANJA: 'grupacijaPitanja/UPDATE_GRUPACIJAPITANJA',
  DELETE_GRUPACIJAPITANJA: 'grupacijaPitanja/DELETE_GRUPACIJAPITANJA',
  RESET: 'grupacijaPitanja/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGrupacijaPitanja>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GrupacijaPitanjaState = Readonly<typeof initialState>;

// Reducer

export default (state: GrupacijaPitanjaState = initialState, action): GrupacijaPitanjaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_GRUPACIJAPITANJAS):
    case REQUEST(ACTION_TYPES.FETCH_GRUPACIJAPITANJA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GRUPACIJAPITANJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GRUPACIJAPITANJA):
    case REQUEST(ACTION_TYPES.UPDATE_GRUPACIJAPITANJA):
    case REQUEST(ACTION_TYPES.DELETE_GRUPACIJAPITANJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_GRUPACIJAPITANJAS):
    case FAILURE(ACTION_TYPES.FETCH_GRUPACIJAPITANJA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GRUPACIJAPITANJA):
    case FAILURE(ACTION_TYPES.CREATE_GRUPACIJAPITANJA):
    case FAILURE(ACTION_TYPES.UPDATE_GRUPACIJAPITANJA):
    case FAILURE(ACTION_TYPES.DELETE_GRUPACIJAPITANJA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_GRUPACIJAPITANJAS):
    case SUCCESS(ACTION_TYPES.FETCH_GRUPACIJAPITANJA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPACIJAPITANJA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GRUPACIJAPITANJA):
    case SUCCESS(ACTION_TYPES.UPDATE_GRUPACIJAPITANJA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GRUPACIJAPITANJA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/grupacija-pitanjas';
const apiSearchUrl = 'api/_search/grupacija-pitanjas';

// Actions

export const getSearchEntities: ICrudSearchAction<IGrupacijaPitanja> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_GRUPACIJAPITANJAS,
  payload: axios.get<IGrupacijaPitanja>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IGrupacijaPitanja> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GRUPACIJAPITANJA_LIST,
  payload: axios.get<IGrupacijaPitanja>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGrupacijaPitanja> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GRUPACIJAPITANJA,
    payload: axios.get<IGrupacijaPitanja>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGrupacijaPitanja> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GRUPACIJAPITANJA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGrupacijaPitanja> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GRUPACIJAPITANJA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGrupacijaPitanja> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GRUPACIJAPITANJA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
