import * as Hapi from '@hapi/hapi';
import validate from '../request/validate';
import Logger from '../../helper/logger';
import IRoute from '../../helper/route';
import RequestController from './controller';

export default class RequestRoutes implements IRoute {
  public async register(server: Hapi.Server): Promise<any> {
    return new Promise(resolve => {
      Logger.info('Request - Start adding routes');

      const controller = new RequestController();

      server.route([
        {
          method: 'GET',
          path: `/api/cloud-providers/{id}/{resourceId}/request`,
          options: {
            handler: controller.getRequestById,
            validate: validate.getRequestById,
            description: 'Method that gets Request Yaml by its Cloud Provider ID & Resource ID.',
            tags: ['api', 'request Yaml'],
            auth: false,
          },
        },
        {
          method: 'PUT',
          path: `/api/cloud-providers/{id}/{resourceId}/request`,
          options: {
            handler: controller.updateRequestById,
            validate: validate.updateRequestById,
            description: 'Method that updates Request Yaml by its Cloud Provider ID & Resource ID.',
            tags: ['api', 'request Yaml'],
            auth: false,
          },
        }
      ]);

      Logger.info('Request - Finish adding routes');

      resolve();
    });
  }
}
