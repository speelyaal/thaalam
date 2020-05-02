import * as Hapi from '@hapi/hapi';
import validate from '../response/validate';
import Logger from '../../helper/logger';
import IRoute from '../../helper/route';
import ResponseController from './controller';

export default class ResponseRoutes implements IRoute {
  public async register(server: Hapi.Server): Promise<any> {
    return new Promise(resolve => {
      Logger.info('Response - Start adding routes');

      const controller = new ResponseController();

      server.route([
        {
          method: 'GET',
          path: `/api/cloud-providers/{id}/{resourceId}/response`,
          options: {
            handler: controller.getResponseById,
            validate: validate.getResponseById,
            description: 'Method that gets Response Yaml by its Cloud Provider ID & Resource ID.',
            tags: ['api', 'response Yaml'],
            auth: false,
          },
        },
        {
          method: 'PUT',
          path: `/api/cloud-providers/{id}/{resourceId}/response`,
          options: {
            handler: controller.updateResponseById,
            validate: validate.updateResponseById,
            description: 'Method that updates Response Yaml by its Cloud Provider ID & Resource ID.',
            tags: ['api', 'response Yaml'],
            auth: false,
          },
        }
      ]);

      Logger.info('Response - Finish adding routes');

      resolve();
    });
  }
}
