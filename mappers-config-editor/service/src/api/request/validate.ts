import * as Joi from '@hapi/joi';

export default {
  getRequestById: {
    params: {
      id: Joi.string().required(),
      resourceId: Joi.string().required()
    },
  },
  updateRequestById: {
    params: {
      id: Joi.string().required(),
      resourceId: Joi.string().required()
    }
  }
};
