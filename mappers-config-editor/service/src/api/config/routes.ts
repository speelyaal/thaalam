import * as Hapi from '@hapi/hapi';
import validate from '../config/validate';
import Logger from '../../helper/logger';
import IRoute from '../../helper/route';
import ConfigController from './controller';

export default class ConfigRoutes implements IRoute {
  public async register(server: Hapi.Server): Promise<any> {
    return new Promise(resolve => {
      Logger.info('Config - Start adding routes');

      const controller = new ConfigController();

      server.route([
        {
          method: 'GET',
          path: `/api/cloud-providers/config/{id}`,
          options: {
            handler: controller.getConfigById,
            validate: validate.getConfigById,
            description: 'Method that gets Config by its Cloud Provider ID.',
            tags: ['api', 'config'],
            auth: false,
          },
        },
        // {
        //   method: 'POST',
        //   path: `/api/cloud-providers/config`,
        //   options: {
        //     handler: controller.createConfig,
        //     validate: validate.createConfig,
        //     description: 'Method that updates Config by its Cloud Provider ID.',
        //     tags: ['api', 'config'],
        //     auth: false,
        //   },
        // },
        {
          method: 'PUT',
          path: `/api/cloud-providers/config/{id}`,
          options: {
            handler: controller.updateConfigById,
            validate: validate.updateConfigById,
            description: 'Method that updates Config by its Cloud Provider ID.',
            tags: ['api', 'config'],
            auth: false,
          },
        }
      ]);

      Logger.info('Config - Finish adding routes');

      resolve();
    });
  }
}
