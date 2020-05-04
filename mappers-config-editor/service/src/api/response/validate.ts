import * as Joi from '@hapi/joi';

export default {
  getResponseById: {
    params: {
      id: Joi.string().required(),
      resourceId: Joi.string().required()
    },
  },
  updateResponseById: {
    params: {
      id: Joi.string().required(),
      resourceId: Joi.string().required()
    }
  }
};
