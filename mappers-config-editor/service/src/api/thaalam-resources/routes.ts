import * as Hapi from '@hapi/hapi';
import validate from '../thaalam-resources/validate';
import Logger from '../../helper/logger';
import IRoute from '../../helper/route';
import ThaalamResourcesController from './controller';

export default class ThaalamResourcesRoutes implements IRoute {
  public async register(server: Hapi.Server): Promise<any> {
    return new Promise(resolve => {
      Logger.info('thaalam-resources - Start adding routes');

      const controller = new ThaalamResourcesController();

      server.route([
        {
          method: 'GET',
          path: `/api/thaalam-resources`,
          options: {
            handler: controller.getThaalamResources,
            description: 'Method that gets thaalam-resources.',
            tags: ['api', 'thaalam-resources'],
            auth: false,
          },
        }
      ]);

      Logger.info('thaalam-resources - Finish adding routes');

      resolve();
    });
  }
}
