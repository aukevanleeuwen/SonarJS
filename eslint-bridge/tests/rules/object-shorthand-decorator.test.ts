/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2021 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import { Linter, RuleTester } from 'eslint';
import { decorateObjectShorthand } from 'rules/object-shorthand-decorator';

const rule = decorateObjectShorthand(new Linter().getRules().get('object-shorthand'));
const ruleTester = new RuleTester({
  parser: require.resolve('@typescript-eslint/parser'),
  parserOptions: { ecmaVersion: 2018 },
});

ruleTester.run(`Object literal shorthand syntax should be used`, rule, {
  valid: [
    {
      code: `const obj = { foo };`,
    },
    {
      code: `
      ({
        foo: function(component, event, helper) {}
      });
      `,
    },
  ],
  invalid: [
    {
      code: `const obj = { foo: foo };`,
      output: `const obj = { foo };`,
      errors: 1,
    },
    {
      code: `({ foo: foo });`,
      output: `({ foo });`,
      errors: 1,
    },
  ],
});
