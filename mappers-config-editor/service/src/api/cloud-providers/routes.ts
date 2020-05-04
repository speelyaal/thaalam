import * as Hapi from '@hapi/hapi';
import validate from '../cloud-providers/validate';
import Logger from '../../helper/logger';
import IRoute from '../../helper/route';
import CloudProviderController from './controller';

export default class CloudProvidersRoutes implements IRoute {
  public async register(server: Hapi.Server): Promise<any> {
    return new Promise(resolve => {
      Logger.info('CloudProvidersRoutes - Start adding routes');

      const controller = new CloudProviderController();

      server.route([
        {
          method: 'GET',
          path: `/api/test-driver`,
          options: {
            handler: controller.testDriver,
            description: 'Method used as Test-Driver.',
            tags: ['api', 'Test-Driver'],
            auth: false,
          },
        },
        {
          method: 'GET',
          path: `/api/cloud-providers`,
          options: {
            handler: controller.getAll,
            description: 'Method that gets all supported Cloud Providers.',
            tags: ['api', 'cloud-providers'],
            auth: false,
          },
        },
        {
          method: 'POST',
          path: '/api/cloud-providers',
          options: {
            handler: controller.createCloudProvider,
            validate: validate.createCloudProvider,
            description: 'Method that creates a new Cloud Provider.',
            tags: ['api', 'cloud-providers'],
            auth: false,
          },
        }
      ]);

      Logger.info('CloudProvidersRoutes - Finish adding routes');

      resolve();
    });
  }
}
