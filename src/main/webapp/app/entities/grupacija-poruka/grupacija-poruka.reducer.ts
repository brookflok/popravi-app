import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGrupacijaPoruka, defaultValue } from 'app/shared/model/grupacija-poruka.model';

export const ACTION_TYPES = {
  SEARCH_GRUPACIJAPORUKAS: 'grupacijaPoruka/SEARCH_GRUPACIJAPORUKAS',
  FETCH_GRUPACIJAPORUKA_LIST: 'grupacijaPoruka/FETCH_GRUPACIJAPORUKA_LIST',
  FETCH_GRUPACIJAPORUKA: 'grupacijaPoruka/FETCH_GRUPACIJAPORUKA',
  CREATE_GRUPACIJAPORUKA: 'grupacijaPoruka/CREATE_GRUPACIJAPORUKA',
  UPDATE_GRUPACIJAPORUKA: 'grupacijaPoruka/UPDATE_GRUPACIJAPORUKA',
  DELETE_GRUPACIJAPORUKA: 'grupacijaPoruka/DELETE_GRUPACIJAPORUKA',
  RESET: 'grupacijaPoruka/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGrupacijaPoruka>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GrupacijaPorukaState = Readonly<typeof initialState>;

// Reducer

export default (state: GrupacijaPorukaState = initialState, action): GrupacijaPorukaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_GRUPACIJAPORUKAS):
    case REQUEST(ACTION_TYPES.FETCH_GRUPACIJAPORUKA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GRUPACIJAPORUKA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GRUPACIJAPORUKA):
    case REQUEST(ACTION_TYPES.UPDATE_GRUPACIJAPORUKA):
    case REQUEST(ACTION_TYPES.DELETE_GRUPACIJAPORUKA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_GRUPACIJAPORUKAS):
    case FAILURE(ACTION_TYPES.FETCH_GRUPACIJAPORUKA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GRUPACIJAPORUKA):
    case FAILURE(ACTION_TYPES.CREATE_GRUPACIJAPORUKA):
    case FAILURE(ACTION_TYPES.UPDATE_GRUPACIJAPORUKA):
    case FAILURE(ACTION_TYPES.DELETE_GRUPACIJAPORUKA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_GRUPACIJAPORUKAS):
    case SUCCESS(ACTION_TYPES.FETCH_GRUPACIJAPORUKA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GRUPACIJAPORUKA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GRUPACIJAPORUKA):
    case SUCCESS(ACTION_TYPES.UPDATE_GRUPACIJAPORUKA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GRUPACIJAPORUKA):
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

const apiUrl = 'api/grupacija-porukas';
const apiSearchUrl = 'api/_search/grupacija-porukas';

// Actions

export const getSearchEntities: ICrudSearchAction<IGrupacijaPoruka> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_GRUPACIJAPORUKAS,
  payload: axios.get<IGrupacijaPoruka>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IGrupacijaPoruka> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GRUPACIJAPORUKA_LIST,
  payload: axios.get<IGrupacijaPoruka>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGrupacijaPoruka> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GRUPACIJAPORUKA,
    payload: axios.get<IGrupacijaPoruka>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGrupacijaPoruka> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GRUPACIJAPORUKA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGrupacijaPoruka> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GRUPACIJAPORUKA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGrupacijaPoruka> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GRUPACIJAPORUKA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
