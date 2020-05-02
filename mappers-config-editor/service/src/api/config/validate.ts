import * as Joi from '@hapi/joi';

export default {
  getConfigById: {
    params: {
      id: Joi.string().required(),
    },
  },
  updateConfigById: {
    params: {
      id: Joi.string().required(),
    }
  }
};
