import * as Joi from '@hapi/joi';

export default {  
  createCloudProvider: {
    payload: {
      name: Joi.string().required()
    },
  }
};
