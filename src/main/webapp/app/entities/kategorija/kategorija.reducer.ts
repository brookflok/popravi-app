import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKategorija, defaultValue } from 'app/shared/model/kategorija.model';

export const ACTION_TYPES = {
  SEARCH_KATEGORIJAS: 'kategorija/SEARCH_KATEGORIJAS',
  FETCH_KATEGORIJA_LIST: 'kategorija/FETCH_KATEGORIJA_LIST',
  FETCH_KATEGORIJA: 'kategorija/FETCH_KATEGORIJA',
  CREATE_KATEGORIJA: 'kategorija/CREATE_KATEGORIJA',
  UPDATE_KATEGORIJA: 'kategorija/UPDATE_KATEGORIJA',
  DELETE_KATEGORIJA: 'kategorija/DELETE_KATEGORIJA',
  RESET: 'kategorija/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKategorija>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type KategorijaState = Readonly<typeof initialState>;

// Reducer

export default (state: KategorijaState = initialState, action): KategorijaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_KATEGORIJAS):
    case REQUEST(ACTION_TYPES.FETCH_KATEGORIJA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KATEGORIJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_KATEGORIJA):
    case REQUEST(ACTION_TYPES.UPDATE_KATEGORIJA):
    case REQUEST(ACTION_TYPES.DELETE_KATEGORIJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_KATEGORIJAS):
    case FAILURE(ACTION_TYPES.FETCH_KATEGORIJA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KATEGORIJA):
    case FAILURE(ACTION_TYPES.CREATE_KATEGORIJA):
    case FAILURE(ACTION_TYPES.UPDATE_KATEGORIJA):
    case FAILURE(ACTION_TYPES.DELETE_KATEGORIJA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_KATEGORIJAS):
    case SUCCESS(ACTION_TYPES.FETCH_KATEGORIJA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_KATEGORIJA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_KATEGORIJA):
    case SUCCESS(ACTION_TYPES.UPDATE_KATEGORIJA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_KATEGORIJA):
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

const apiUrl = 'api/kategorijas';
const apiSearchUrl = 'api/_search/kategorijas';

// Actions

export const getSearchEntities: ICrudSearchAction<IKategorija> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_KATEGORIJAS,
  payload: axios.get<IKategorija>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IKategorija> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_KATEGORIJA_LIST,
  payload: axios.get<IKategorija>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IKategorija> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KATEGORIJA,
    payload: axios.get<IKategorija>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IKategorija> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KATEGORIJA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IKategorija> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KATEGORIJA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKategorija> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KATEGORIJA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
