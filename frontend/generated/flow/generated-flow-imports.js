import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '540c54cc51da1b2f84dd693c67ff1986a0c15d55aa66a81f0b8fb450713b83d1') {
    pending.push(import('./chunks/chunk-540c54cc51da1b2f84dd693c67ff1986a0c15d55aa66a81f0b8fb450713b83d1.js'));
  }
  if (key === '664a603d9f57dc30d53add736a9151e496e14d8e9dcaaf2f0fe4e0b30c919b7d') {
    pending.push(import('./chunks/chunk-664a603d9f57dc30d53add736a9151e496e14d8e9dcaaf2f0fe4e0b30c919b7d.js'));
  }
  if (key === '77728d3e7227484c7a6d1ba20c2e9c057e9d60f6d85e8db6ad7cef31d83f3622') {
    pending.push(import('./chunks/chunk-77728d3e7227484c7a6d1ba20c2e9c057e9d60f6d85e8db6ad7cef31d83f3622.js'));
  }
  if (key === '58735205e18c2b0ec908cbe4de26ec531544ed7f9008d7225c35ecc87d401026') {
    pending.push(import('./chunks/chunk-58735205e18c2b0ec908cbe4de26ec531544ed7f9008d7225c35ecc87d401026.js'));
  }
  if (key === 'e3e656ecc8fbe2f80aace41e661e7be41f5cb93377826f3ec8f2c53be12addc8') {
    pending.push(import('./chunks/chunk-e3e656ecc8fbe2f80aace41e661e7be41f5cb93377826f3ec8f2c53be12addc8.js'));
  }
  if (key === 'e846a2f108b6c0e34c6b1ec0a19558a4f1f03f4928fe8a3df89c5c6e17ab3046') {
    pending.push(import('./chunks/chunk-e846a2f108b6c0e34c6b1ec0a19558a4f1f03f4928fe8a3df89c5c6e17ab3046.js'));
  }
  if (key === '09f3f32cc53e2debb86a1e6bb11a58fc4fada540cd42d4ee770c8928a9b6d856') {
    pending.push(import('./chunks/chunk-09f3f32cc53e2debb86a1e6bb11a58fc4fada540cd42d4ee770c8928a9b6d856.js'));
  }
  if (key === '7e670ec4f7d5ab8531563dda529468c5d4746a0833e7453c4b56b48c3ebd55bf') {
    pending.push(import('./chunks/chunk-7e670ec4f7d5ab8531563dda529468c5d4746a0833e7453c4b56b48c3ebd55bf.js'));
  }
  if (key === '001a21199f2f3c616d82ab1450179b861290bfa0f4058b3822fbfd86c86d5394') {
    pending.push(import('./chunks/chunk-001a21199f2f3c616d82ab1450179b861290bfa0f4058b3822fbfd86c86d5394.js'));
  }
  if (key === '82ce657f85a7da466c2985ac5506c1ac9887f62168702103077aeee1256e8ebe') {
    pending.push(import('./chunks/chunk-82ce657f85a7da466c2985ac5506c1ac9887f62168702103077aeee1256e8ebe.js'));
  }
  if (key === 'c4d091ecf8fc7f47d66f58382f456ca5950a4184444b9cb57656a294b0b47874') {
    pending.push(import('./chunks/chunk-c4d091ecf8fc7f47d66f58382f456ca5950a4184444b9cb57656a294b0b47874.js'));
  }
  if (key === 'f4f6b4ab5e7e6f9f532abd6251f98cb091f089728fa8e7d86b571284a24b4aca') {
    pending.push(import('./chunks/chunk-f4f6b4ab5e7e6f9f532abd6251f98cb091f089728fa8e7d86b571284a24b4aca.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;